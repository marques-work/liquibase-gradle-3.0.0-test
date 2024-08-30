# vim: ts=2 sts=2 sw=2 noet ai

.DEFAULT_GOAL := help

.PHONY: help
help:               ## Show this help message
	@grep -E '^[.a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN { FS = ":.*?## " }; { lines[FNR]=$$1":##"$$2; len=length($$1); if (len > max) max=len; ++c; } END { FS=":##";fmt="\033[36;1m%-"max"s\033[37;1m    %s\033[0m\n"; for(i=1;i<=c;++i){$$0=lines[i]; printf(fmt, $$1, $$2) } }'

.PHONY: setup
setup:              ## Bootstrap containers
	@docker compose up -d

.PHONY: teardown
teardown:           ## Tear down containers
	@docker compose down

.PHONY: migrate
migrate:            ## Run liquibase migrations
	@./gradlew update --info

.PHONY: psql
psql:               ## Connect to the database with a `psql` session
	@docker container exec -it postgresql psql -U postgres -h localhost -d postgres
