FROM devopsedu/webapp
MAINTAINER "Vignesh"
ADD website /var/www/html
RUN rm /var/www/html/index.html
RUN apt update && \
    apt install -y php
CMD apachectl -D FOREGROUND
