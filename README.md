# BarrioDigital — Microservicio de Trámites

Microservicio de dominio del sistema BarrioDigital, encargado de los trámites vecinales. Construido con Spring
Boot y Spring Data JPA sobre Oracle Autonomous Database.

## Responsabilidad

CRUD de trámites y control de su máquina de estados:

```
INGRESADO -> ADMITIDO -> EN_GESTION -> EN_TERRENO -> RESUELTO / RECHAZADO
```

No valida JWT ni aplica reglas de autorización — eso lo hacen el API Gateway y el BFF. Este servicio es interno y
solo lo consume `ms-barriodigital-bff`.

## Endpoints

- `GET /api/requests` — lista todos los trámites (o filtra por `vecinoId`).
- `GET /api/requests/{id}` — obtiene un trámite.
- `POST /api/requests` — crea un trámite nuevo (queda en estado `INGRESADO`).
- `PUT /api/requests/{id}/estado` — cambia el estado, validando que la transición sea permitida.

## Configuración

Corre en el puerto `8081`. Necesita las variables de entorno `ORACLE_DB_URL`, `ORACLE_DB_USER` y
`ORACLE_DB_PASSWORD` para conectarse a Oracle Autonomous Database.

## Ejecutar localmente

```bash
./mvnw spring-boot:run
```

## Compilar

```bash
./mvnw clean package
```
