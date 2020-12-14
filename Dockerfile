FROM maven:3.6.3-jdk-11-slim
ARG MYUSER=app
ARG MYGROUP=app

RUN groupadd -g 1000 app \
 && useradd -g 1000 -u 1000 -d /home/app -s /bin/bash app

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN echo \
    "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0\' \
    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' \
    xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd'> \
        <localRepository>/home/app/.m2/repository</localRepository> \
        <interactiveMode>true</interactiveMode> \
        <usePluginRegistry>false</usePluginRegistry> \
        <offline>false</offline> \
    </settings>" \
    > /usr/share/maven/conf/settings.xml;
    
RUN chmod +x /usr/local/bin/entrypoint.sh

#Start application
WORKDIR /project
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
# RUN chown -R app:app ./
# RUN chown -R app:app /home/app/.m2



CMD ["bash"] 

USER $MYUSER