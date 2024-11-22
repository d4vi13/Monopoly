# Diretórios
SRC_DIR := Nucleo
BIN_DIR := bin
LIB_DIR := lib
LIBS := $(LIB_DIR)/jackson-annotations-2.18.1.jar:$(LIB_DIR)/jackson-core-2.18.1.jar:$(LIB_DIR)/jackson-databind-2.18.1.jar

# Classe principal
MAIN_CLASS := Nucleo.Jogo

# Alvos principais
.PHONY: all clean run zip

# Compilar tudo
all: $(BIN_DIR)
	@echo "Compilando o projeto..."
	@find $(SRC_DIR) -name "*.java" > sources.txt
	@javac -d $(BIN_DIR) -cp $(LIBS) @sources.txt
	@rm -f sources.txt
	@echo "Compilação concluída com sucesso!"

$(BIN_DIR):
	@mkdir -p $(BIN_DIR)

# Limpar arquivos compilados
clean:
	@echo "Limpando arquivos compilados..."
	@rm -rf $(BIN_DIR)
	@echo "Diretório de compilação limpo!"

# Executar o programa principal
run: all
	@echo "Executando o programa principal..."
	@java -cp $(BIN_DIR):$(LIBS) $(MAIN_CLASS)
	@echo "Execução concluída!"

# Compactar o projeto em um arquivo .zip (opcional)
zip:
	@echo "Compactando o projeto em Monopoly.zip..."
	@zip -r Monopoly.zip $(SRC_DIR) Dados Makefile README.md
	@echo "Projeto compactado!"
