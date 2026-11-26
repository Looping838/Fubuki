FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y --no-install-recommends \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxcursor1 \
    fontconfig \
    fonts-liberation \
    fonts-noto-core \
    adwaita-icon-theme \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN rm -rf bin && mkdir bin && javac -d bin -encoding UTF-8 @compile.list

CMD ["java", "-cp", "bin", "controleur.Controleur"]