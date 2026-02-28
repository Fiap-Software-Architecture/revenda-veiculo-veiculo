CREATE TABLE IF NOT EXISTS veiculos (
    id UUID PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE, 
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(150) NOT NULL,
    ano INTEGER NOT NULL,
    cor VARCHAR(50) NOT NULL,
    preco NUMERIC(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_compras_veiculo_id ON compras (veiculo_id);