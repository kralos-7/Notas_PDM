#!/bin/bash

# Nombre del driver JDBC
JAR="sqlite-jdbc-3.36.0.3.jar"

# Verificar si el JAR existe
if [[ ! -f "$JAR" ]]; then
  echo "❌ No se encuentra $JAR en el directorio actual."
  echo "📦 Descárgalo desde:"
  echo "👉 https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.36.0.3"
  exit 1
fi

echo "🔧 Compilando clases Singleton..."
javac -cp ".:$JAR" SQLiteConSingleton.java ConSingleton.java

if [[ $? -ne 0 ]]; then
  echo "❌ Error durante la compilación"
  exit 1
fi

echo "🚀 Ejecutando programa con Singleton..."
java -cp ".:$JAR" ConSingleton

