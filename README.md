# AnimalShelter

## Beskrivning
Detta projekt är en WebService i form av ett API som hanterar logistik, Crud-operationer, för olika djurhem för att de enklare skall kunna ha koll på både vilka djur som finns var, men också vilka andra djurhem som finns tillgängliga.
Projektet är en Maven Spring Boot-applikation med CI/CD pipeline via GitHub Actions och AWS. Syftet är att automatisera bygg-, test- och deploy-processen i en applikation.
Till sist skulle detta API bli föremål för en annan grupps arbete med att skapa en klient som läser mot AnimalShelterAPI via AWS för att utföra crud-operationer på vår uppsatta databas.

I denna grupp, GRUPP 1, finner vi följande medelmmar:
Elias, Linus, Samuel, Sandra K och Sandra M

### Teknisk Specifikation
- Tekniker: Java, Maven, Spring Boot, GitHub Actions, AWS, JSON Web Tokens.
- CI/CD Tools: GitHub Actions och AWS.
- Testning : Github Actions och AWS samt Unittester på Controllerklasser.

- Länk till Applikationen genom AWS EC2: http://animalshelterapi-env.eba-mbz3mefy.eu-north-1.elasticbeanstalk.com

- Länk till vår grupps klient: https://github.com/Lajjaan/FleaMarketService/tree/dev

## Installation
1: Besök projektet på GitHub via länken: https://github.com/SandraKorpi/AnimalShelter/tree/dev

2: Clona ned repot till din egen dator via cmd commando : "git clone https://github.com/SandraKorpi/AnimalShelter.git"

3: Öppna projektet i önsvärt IDE.

4: Manövrera dig till dev branchen! Detta kan göras via git commandot git checkout dev.

5: Kör projektet.


## CI/CD
Nedan beskrivs CI/CD processen med förklaringar av de Noder som sekvensen går igenom för att kunna automatisera CI/CD processen, och på så sätt hosta hela applikationen i Molnet. Det är hela ledet från där källkoden ligger i Repot till att det driftas och hostas på AWS i deras molntjänst.

### Github Actions:

**beskrivning och bilder behövs här!

### AWS Deployment Process:
**beskrivning och bilder behövs här!
- AWS Pipeline:

- AWS CodeBuild:


- AWS BeanStalk:


- AWS RDS kommer nedan.


## EndPoints:
Vårt projekt använder Swagger för att enklare kunna dokumentera API:ets Endpoints. Nedan finns instruktion samt bild på alla enpoings som projektet brukar.

  länk till applikationen och dess endpoints:  http://animalshelterapi-env.eba-mbz3mefy.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html

- Bild 1: (vi måste inserta print screens på endpoints nedan)!

- Bild 2:

- Bild 3:

## JWT, Authentication och Login.
(förklaring behövs här)

## Databas.
Databasen är en MySQL databas som är skapad via AWS Amazon RDS - Relational Database Service.

- Bild tagen från AWS:
-![image](https://github.com/user-attachments/assets/6098c403-88ac-43ee-92ab-b66258624e11)

- Bild tagen från MySQL Workbench:

 ![image](https://github.com/user-attachments/assets/d2bfc7f1-a6a4-425c-9929-7636163e3a0e)

![image](https://github.com/user-attachments/assets/3cb667d0-933a-4994-a448-93a377cd9f89)

![image](https://github.com/user-attachments/assets/1429594f-520b-42fb-a85c-c2f03cac353c)


## Testning:
I CI/CD ingår det att varje kodändring som triggas av en PUSH-event går igenom UnitTester för att säkerställa att koden är säker och följer kvaliteten som är uppsatt i Definition of Done. I detta projekt finns därför några få tester till vardera Service klass.

