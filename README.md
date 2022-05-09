* Clone Repository

* Add kafka and zookeeper hostnames to point at your machine ip in your hosts file

** linux / mac -> the file is located at /etc/hosts
** windows -> the file is located at c:\Windows\System32\Drivers\etc\hosts

* Bring up the kafka cluster
** > docker build .\apache-kafka\kafka-common\ -t kafka-common 
** > docker-compose up

* Navigate to localhost:9000 to use kafdrop