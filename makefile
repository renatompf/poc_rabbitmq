rabbitMQ:
	docker compose up rabbitMQ;

rest:
	docker compose build; docker compose up app-rest;

calculator:
	docker compose build; docker compose up app-calculator;

app:
	docker compose build; docker compose up app-rest; docker compose up app-calculator;

all:
	docker compose build; docker compose up;