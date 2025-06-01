#!/bin/bash

# Nombre del archivo JAR del driver SQLite
JAR="sqlite-jdbc-3.36.0.3.jar"

# Verificar si el JAR existe
if [[ ! -f "$JAR" ]]; then
  echo "❌ No se encuentra $JAR en el directorio actual."
  echo "📦 Descárgalo desde:"
  echo "👉 https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.36.0.3"
  exit 1
fi

echo "🔧 Compilando..."
javac -cp ".:$JAR" SQLiteSinSingleton.java SinSingleton.java

if [[ $? -ne 0 ]]; then
  echo "❌ Error de compilación"
  exit 1
fi

echo "🚀 Ejecutando..."
java -cp ".:$JAR" SinSingleton

