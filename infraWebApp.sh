# ==================== CONFIGURAÇÃO DO WEB APP ====================

# Variáveis
grupoRecursos=rg-greenon
regiao=eastus
planService=PlanGreenOn
sku=F1
appName=greenonRM552258
runtime="TOMCAT:10.1-java17"  

### Criação do Grupo de Recursos
# Verifica a existência do grupo de recursos e se não existir, cria
if [ $(az group exists --name $grupoRecursos) = true ]; then
    echo "O grupo de recursos $grupoRecursos já existe"
else
    # Cria o grupo de recursos
    az group create --name $grupoRecursos --location $regiao
    echo "Grupo de recursos $grupoRecursos criado na localização $regiao"
fi

### Cria o Plano de Serviço se não existir
if az appservice plan show --name $planService --resource-group $grupoRecursos &> /dev/null; then
    echo "O plano de serviço $planService já existe"
else
    az appservice plan create --name $planService --resource-group $grupoRecursos --sku $sku
    echo "Plano de serviço $planService criado com sucesso"
fi 

### Cria o Serviço de Aplicativo se não existir
if az webapp show --name $appName --resource-group $grupoRecursos &> /dev/null; then
    echo "O Serviço de Aplicativo $appName já existe"
else
    az webapp create --resource-group $grupoRecursos --plan $planService --name $appName --runtime $runtime
    echo "Serviço de Aplicativo $appName criado com sucesso com runtime Java 17"
fi

### Configura o Serviço de Aplicativo para Java 17
if az webapp show --name $appName --resource-group $grupoRecursos > /dev/null 2>&1; then
    az webapp config set --resource-group $grupoRecursos --name $appName --java-version 17
    echo "Serviço de Aplicativo $appName configurado com Java 17 com sucesso"
fi
