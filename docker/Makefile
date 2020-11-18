SHELL = /bin/bash
.DEFAULT_GOAL := help
JENKINS_URL ?= localhost
JJB_CONFIG ?= jenkins_jobs.ini

.PHONY: help
help:
	@echo "Tools for managing Jenkins"
	@echo ""
	@echo "Targets:"
	@echo ""
	@echo "  create"
	@echo "    Creates a local version of Jenkins with docker-compose"
	@echo ""
	@echo "  destroy"
	@echo "    Tearsdown a local version of Jenkins with docker-compose"
	@echo ""
	@echo "  shell"
	@echo "    Shell into Jenkins docker"
	@echo ""
	@echo "  admin-key"
	@echo "    Needed for initial Jenkins initialization"

,PHONY: check
check:
	@echo -n "Are you sure? [y/N] " && read ans && [ $${ans:-N} = y ]

.PHONY: create
create:
	@-docker-compose -f docker-compose.yml up -d
	@echo "Restarting network after 5 seconds"
	@sleep 5
	@-docker-compose -f docker-compose.yml exec master /etc/init.d/dnsmasq restart
	@echo "Waiting 20 seconds for Jenkins Key to come up"
	@sleep 20
	@echo "Here is the key"
	@$(MAKE) admin-key

.PHONY: destroy
destroy:
	@-docker-compose -f docker-compose.yml down --rmi all

.PHONY: admin-key
admin-key:
	@-docker-compose -f docker-compose.yml exec master cat /var/jenkins_home/secrets/initialAdminPassword

.PHONY: shell
shell:
	@-docker-compose -f docker-compose.yml exec master bash

.PHONY: clean
clean: destroy-jenkins
	@rm -rf .venv
	@docker volume rm $(docker volume ls -q --filter dangling=true)
