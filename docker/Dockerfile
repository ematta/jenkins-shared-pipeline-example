FROM jenkins/jenkins:lts
# Files to copy
COPY executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy
COPY log.properties /var/jenkins_home/log.properties
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

# Image Setup
USER root
RUN apt-get update && \
    apt-get -y install apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    vim \
    less \
    dnsmasq \
    software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
    "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
    $(lsb_release -cs) \
    stable" && \
    apt-get update && \
    apt-get -y install docker-ce
RUN usermod -a -G docker jenkins

# DNSMasq
USER root
RUN echo "conf-dir=/etc/dnsmasq.d,.bak" >> /etc/dnsmasq.conf
RUN echo "no-resolv" >> /etc/dnsmasq.conf
RUN echo "server=8.8.8.8" >> /etc/dnsmasq.conf
RUN echo "server=8.8.4.4" >> /etc/dnsmasq.conf
COPY development.conf /etc/dnsmasq.d/development.conf
# RUN /etc/init.d/dnsmasq restart

# Back to Jenkins setup
USER jenkins
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt