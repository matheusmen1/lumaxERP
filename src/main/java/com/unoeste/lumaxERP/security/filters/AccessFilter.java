package com.unoeste.lumaxERP.security.filters;

import java.io.IOException;

import com.unoeste.lumaxERP.security.JWTTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        String token = req.getHeader("Authorization");
        if(token!=null && JWTTokenProvider.verifyToken(token))
        {
            Claims claims = JWTTokenProvider.getAllClaimsFromToken(token);
            Integer nivel = claims.get("nivel", Integer.class);
            String path = req.getRequestURI();
            String metodo = req.getMethod();

            // exclusao de vendas, entradas de estoque e lancamentos apenas para administradores (nivel 1)
            if (metodo.equals("DELETE")
                    && (path.startsWith("/apis/venda") || path.startsWith("/apis/entrada-estoque") || path.startsWith("/apis/lancamento"))
                    && (nivel == null || nivel != 1))
            {
                ((HttpServletResponse)response).setStatus(403);
                response.getOutputStream().write("Não autorizado ".getBytes());
                return;
            }

            if (path.startsWith("/apis/usuario") && (nivel == null || nivel != 1)) // acesso aos usuarios apenas para os adm (nivel 1)
            {
                if (metodo.equals("GET") && !path.matches("/apis/usuario/\\d+"))
                {
                    ((HttpServletResponse)response).setStatus(403);
                    response.getOutputStream().write("Não autorizado ".getBytes());
                    return;
                }
                if (metodo.equals("POST") || metodo.equals("DELETE"))
                {
                    ((HttpServletResponse)response).setStatus(403);
                    response.getOutputStream().write("Não autorizado ".getBytes());
                    return;
                }

            }
            chain.doFilter(request, response);
        }
        else {
            ((HttpServletResponse)response).setStatus(500);
            response.getOutputStream().write("Não autorizado ".getBytes()); 
        }
    }
}
