FROM openjdk:8u181-jre-alpine
RUN apk add --no-cache --virtual .debug-deps bash tmux
COPY ./target/universal/app.zip /
RUN mkdir /app \
    && unzip /app.zip -d / \
    && chmod a+x /app/bin/api \
    && rm /app.zip

CMD /app/bin/api \
    -J-Xmx${JAVA_MAX_HEAP_SIZE:-512m} \
    -Dhttp.port=9000 \
    -Dlogger.file=/app/conf/logback.xml \
    -Dconfig.file=/app/conf/application.conf

VOLUME ["/var/log/api"]
EXPOSE 9000