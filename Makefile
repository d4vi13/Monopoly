# Diretórios
SRC_DIR := nucleo
BIN_DIR := bin

# Classe principal
MAIN_CLASS := Nucleo.Jogo

# Alvos principais
.PHONY: all clean run

# Compilar tudo
all: $(BIN_DIR)
	@echo "Compilando o projeto..."
	@javac -d $(BIN_DIR) ./Nucleo/*.java
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
	@java -cp $(BIN_DIR) $(MAIN_CLASS)
	@echo "Execução concluída!"

# Compactar o projeto em um arquivo .zip (opcional)
zip:
	@echo "Compactando o projeto em Monopoly.zip..."
	@zip -r Monopoly.zip $(SRC_DIR) Dados Makefile README.md
	@echo "Projeto compactado!"