FROM openjdk:11
ADD target/chaoyang-1.0.jar /home/chaoyang-1.0.jar
CMD ["chmod","777","/home/chaoyang-1.0.jar"]
ENTRYPOINT ["java","-jar","/home/chaoyang-1.0.jar"]
#ENTRYPOINT ["nohup","java","-jar","chaoyang-1.0.jar","&"]