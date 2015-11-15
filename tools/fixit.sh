perl -i -pe 's/\r//g;s/[\t ]+$//' $(find -name *.java) build.gradle
