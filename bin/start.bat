SET image_file="image.png"
SET shape="square"
SET outputFile="generated_images/image_"
SET saveOutputs=1
SET pictureMethod=0
SET numOfPicture=50
SET numOfShape=500
SET generation=5000
SET selectionRate=0.30
SET mutuationRate=0.001

java -jar image_evolution.jar %image_file% %shape% %outputFile% %saveOutputs% %pictureMethod% %numOfPicture% %numOfShape% %generation% %selectionRate% %mutuationRate%