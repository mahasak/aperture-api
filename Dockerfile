FROM openjdk:8u181-jre-alpine
RUN apk add --no-cache bash
COPY ./target/universal/app.zip /
RUN mkdir /test && unzip /app.zip -d /

RUN chmod a+x /app/bin/api
RUN rm /app.zip
# for debug docker image
# RUN apk add --no-cache --virtual .debug-deps bash tmux


CMD /app/bin/api \
    -J-Xmx${JAVA_MAX_HEAP_SIZE:-512m} \
    -Dhttp.port=9000 \
    -Dlogger.file=/app/conf/logback.xml \
    -Dconfig.file=/app/conf/application.conf



VOLUME ["/var/log/api"]
EXPOSE 9000