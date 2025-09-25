#!/bin/bash
echo ">> Registrando conector Postgres no Debezium..."
curl -X POST -H "Content-Type: application/json" \
     --data @/kafka/connect/register-postgres.json \
     http://localhost:8083/connectors
