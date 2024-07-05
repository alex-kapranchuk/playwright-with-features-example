FROM maven:3.8.4-openjdk-17-slim

RUN apt-get update && apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    wget \
    unzip \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

RUN curl -sSL https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google-linux-signing-key.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/google-linux-signing-key.gpg arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable

RUN CHROME_DRIVER_VERSION=$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE) \
    && wget -O /tmp/chromedriver.zip "http://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip" \
    && unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/ \
    && rm /tmp/chromedriver.zip


WORKDIR /usr/src/app

COPY . .

RUN mvn clean install

CMD ["sh", "-c", "Xvfb :99 -ac & mvn test -Djava.awt.headless=true -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver"]
