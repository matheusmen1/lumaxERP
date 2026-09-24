-- =========================================================================
-- SISTEMA DE GESTÃO - LOJA DE BATERIAS (POSTGRESQL)
-- =========================================================================

-- 1. Tabelas de Cadastro Base
CREATE TABLE IF NOT EXISTS categoria (
    cat_id BIGSERIAL PRIMARY KEY,
    cat_nome VARCHAR(100) NOT NULL,
    cat_descricao VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS fornecedor (
    for_id BIGSERIAL PRIMARY KEY,
    for_razao_social VARCHAR(150) NOT NULL,
    for_cnpj VARCHAR(14) UNIQUE NOT NULL,
    for_telefone VARCHAR(20) NOT NULL,
    for_email VARCHAR(100) NOT NULL,
    for_endereco VARCHAR(150) NOT NULL,
    for_cidade VARCHAR(100) NOT NULL,
    for_cep VARCHAR(10) NOT NULL,
    for_numero INTEGER NOT NULL,
    for_estado VARCHAR(2) NOT NULL,
    for_bairro VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente (
     cli_id BIGSERIAL PRIMARY KEY,
     cli_nome VARCHAR(150) NOT NULL,
     cli_cpf VARCHAR(15) UNIQUE NOT NULL,
     cli_telefone VARCHAR(20) NOT NULL,
     cli_email VARCHAR(100) NOT NULL,
     cli_endereco VARCHAR(150) NOT NULL,
     cli_cidade VARCHAR(100) NOT NULL,
     cli_cep VARCHAR(10) NOT NULL,
     cli_numero INTEGER NOT NULL,
     cli_estado VARCHAR(2) NOT NULL,
     cli_bairro VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
     prod_id BIGSERIAL PRIMARY KEY,
     prod_nome VARCHAR(100) NOT NULL,
     prod_descricao VARCHAR(100),
     prod_valor NUMERIC(10,2) NOT NULL,
     prod_foto BYTEA,
     cat_id BIGINT NOT NULL,
     for_id BIGINT NOT NULL,
     prod_valor_casco NUMERIC(10, 2),
     CONSTRAINT fk_produto_categoria
         FOREIGN KEY (cat_id)
            REFERENCES categoria (cat_id),
     CONSTRAINT fk_produto_fornecedor
         FOREIGN KEY (for_id)
            REFERENCES fornecedor (for_id)
);

CREATE TABLE IF NOT EXISTS usuario (
     usu_id BIGSERIAL PRIMARY KEY,
     usu_nome VARCHAR(100) NOT NULL,
     usu_email VARCHAR(50) UNIQUE NOT NULL,
     usu_senha VARCHAR(255) NOT NULL,
     usu_nivel INT NOT NULL
);

-- 2. Financeiro Básico
CREATE TABLE IF NOT EXISTS tipo_pagamento (
    tpp_id BIGSERIAL PRIMARY KEY,
    tpp_nome VARCHAR(50) NOT NULL,
    tpp_descricao VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS tipo_despesa (
    tpd_id BIGSERIAL PRIMARY KEY,
    tpd_nome VARCHAR(100) NOT NULL,
    tpd_descricao VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS lancamento (
    lan_id BIGSERIAL PRIMARY KEY,
    lan_data_hora TIMESTAMP NOT NULL,
    lan_valor_total NUMERIC(10,2) NOT NULL,
    lan_observacao VARCHAR(100),
    usu_id BIGINT NOT NULL,
    tpd_id BIGINT NOT NULL,
    CONSTRAINT fk_lancamento_usuario
        FOREIGN KEY (usu_id)
            REFERENCES usuario(usu_id),
    CONSTRAINT fk_lancamento_tipo_despesa
        FOREIGN KEY (tpd_id)
            REFERENCES tipo_despesa(tpd_id)
);

CREATE TABLE IF NOT EXISTS venda (
   ven_id BIGSERIAL PRIMARY KEY,
   ven_data_venda TIMESTAMP NOT NULL,
   ven_valor_total NUMERIC(10,2) NOT NULL,
   cli_id BIGINT NOT NULL,
   usu_id BIGINT NOT NULL,
   tpp_id BIGINT NOT NULL,
   CONSTRAINT fk_venda_cliente
        FOREIGN KEY (cli_id)
            REFERENCES cliente(cli_id),
   CONSTRAINT fk_venda_usuario
        FOREIGN KEY (usu_id)
            REFERENCES usuario(usu_id),
   CONSTRAINT fk_venda_tipo_pagamento
       FOREIGN KEY (tpp_id)
            REFERENCES tipo_pagamento(tpp_id)

);

CREATE TABLE IF NOT EXISTS item_venda (
    itv_id BIGSERIAL PRIMARY KEY,
    itv_quantidade INTEGER NOT NULL,
    itv_valor_unitario NUMERIC(10,2) NOT NULL,
    itv_casco_entregue BOOLEAN NOT NULL,
    itv_numero_serie VARCHAR(100),
    itv_meses_garantia INTEGER,
    ven_id BIGINT NOT NULL,
    prod_id BIGINT NOT NULL,
    CONSTRAINT fk_item_venda_venda
        FOREIGN KEY (ven_id)
            REFERENCES venda(ven_id) ON DELETE CASCADE,
    CONSTRAINT fk_item_venda_produto
        FOREIGN KEY (prod_id)
            REFERENCES produto(prod_id)

);

-- 4. Movimentações de Estoque
CREATE TABLE IF NOT EXISTS entrada_estoque (
    ete_id BIGSERIAL PRIMARY KEY,
    ete_data_entrada TIMESTAMP NOT NULL,
    for_id BIGINT NOT NULL,
    usu_id BIGINT NOT NULL,
    CONSTRAINT fk_entrada_estoque_fornecedor
         FOREIGN KEY (for_id)
                REFERENCES fornecedor(for_id),
    CONSTRAINT fk_entrada_estoque_usuario
         FOREIGN KEY (usu_id)
                REFERENCES usuario(usu_id)
);

CREATE TABLE IF NOT EXISTS item_entrada_estoque (
    iee_id BIGSERIAL PRIMARY KEY,
    iee_quantidade INTEGER NOT NULL,
    iee_custo_unitario NUMERIC(10,2) NOT NULL,
    ete_id BIGINT NOT NULL,
    prod_id BIGINT NOT NULL,

    CONSTRAINT fk_item_entrada_estoque_entrada_estoque
        FOREIGN KEY (ete_id)
            REFERENCES entrada_estoque(ete_id),
    CONSTRAINT fk_item_entrada_estoque_produto
        FOREIGN KEY (prod_id)
            REFERENCES produto(prod_id)
);