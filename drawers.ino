int turnOn(String value);
int turnOnOne(String value);
int turnOnTwo(String value);

void setup() {
    Serial.begin(9600);
    pinMode(D0,OUTPUT);
    pinMode(D1, OUTPUT);
    pinMode(D2, OUTPUT);
    Spark.function("turnOn", turnOn);
    Spark.function("turnOnOne", turnOnOne);
    Spark.function("turnOnTwo", turnOnTwo);
}

void loop() {
}

int turnOn(String value){
    bool val = digitalRead(D0); 
    digitalWrite(D0,!val);
    return 0;
}

int turnOnOne(String value){
    bool val = digitalRead(D1); 
    digitalWrite(D1,!val);
    return 0;
}

int turnOnTwo(String value){
    bool val = digitalRead(D2); 
    digitalWrite(D2,!val);
    return 0;
}

