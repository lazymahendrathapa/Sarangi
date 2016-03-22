# Sarangi
Sarangi is a Music Classification Application that can classify mp3 files based on Genre and Mood.
The feature extraction necessary for the classification is also part of this project. 

## Compilation

Assuming mvn is installed on your machine, just move into the my-app folder and run `mvn compile`

```
cd my-app/
mvn compile
```

## Creating a jar file

In the my-app folder, run `mvn package`
```
cd my-app/
mvn package
```

## Running the application
```
cd my-app/
java -cp .:target/jlayer-1.0.1-1.jar:target/jorbis-0.0.17-2.jar:target/vorbisspi-1.0.3-1.jar:target/tritonus-share-0.3.7-2.jar:target/mp3spi-1.9.5-1.jar:target/gson-2.5.jar:target/my-app-1.0-SNAPSHOT.jar:target/jtransforms-2.4.0.jar com.mycompany.app.App
```
### Things Done 

* Feature Extraction: Intensity, MFCC, Rhythm
* K-means Clustering 

### TODO

* Feature Extraction: Pitch
* K-means Clustering Optimization
* ANN classification
* Code Refactoring
