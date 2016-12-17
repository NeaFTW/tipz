#! /bin/sh

sudo apt-get install -y python-software-properties
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get update -y
echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections
sudo apt-get install -y oracle-java7-installer

curl https://raw.githubusercontent.com/foundweekends/conscript/master/setup.sh | sh
PATH=$PATH:~/bin
export PATH
source ~/.bashrc

cs foundweekends/giter8
brew install giter8