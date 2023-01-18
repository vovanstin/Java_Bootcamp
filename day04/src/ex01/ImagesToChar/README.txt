# Compilation:
    # this command compiles .java files into .class files, put them into target directory
 mkdir target   # javac for java 8 DOES NOT create the directory, however it is required by subject.
 javac -d ./target/ src/java/edu/school21/printer/*/*.java

 cp -rf src/resources target

# Jar file creation:
 rm -f ./target/images-to-chars-printer.jar #if not deleted, there will be a Invalid or corrupt jarfile error
    # this command packs .class files, resources and a manifest file into a single images-to-chars-printer.jar file
 jar cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

    # OPTIONAL: this command will display contents of the manifest file
# unzip -q -c ./target/images-to-chars-printer.jar META-INF/MANIFEST.MF

# Launch jar application:
 java -jar target/images-to-chars-printer.jar . o   #path to file is not needed since it's already packaged