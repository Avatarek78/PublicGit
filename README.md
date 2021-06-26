# Server Client test app

Jak má celá aplikace fungovat :
* Bude použit Maven pro správu závislostí.
* Bude použit Log4J pro logování událostí aplikací (komunikace, volání metod apod.).
* Clientská část bude komunikovat se serverovou částí (komunikace může být zabezpečená
přes SSL – nutnost vygenerování klíče a jeho použití).
* Komunikace bude využívat Hessian protokol, který poskytuje objektovou binární serializaci. A
snadno se používá se Springem (viz zdroje na konci zadání).
* Objekty pro komunikaci budou sdíleny oběma aplikacemi.
## Client:
* Nebude obsahovat DB.
* Bude mít jednoduché webové rozhraní, pomocí kterého půjdou posílat zprávy na
server.
* Webové rozhraní (formulář) bude obsahovat validaci.
* Zpráva by měla být jednoduchý objekt (měla by obsahovat minimálně text a
čas odeslání).
* Mimo klasické posílání zprávy přes formulář bude client posílat i zprávy pravidelně
automaticky pomocí Quartz scheduleru (viz odkazy na konci zadání).
* Po odeslání zprávy na server client zobrazí odpověď od serveru ve webové části.
* Webové rozhraní bude obsahovat jednoduchou historii poslaných zpráv (nemusíš
ukládat nic do databáze – stačí pouze seznam v paměti).
## Server:
* Bude obsahovat DB (PostgreSQL).
* Bude přijímat zprávy od clienta a ukládat tyto zprávy do DB přes Hibernate.
* Bude odpovídat clientovi na jeho zprávy.
* Nemusí obsahovat webové rozhraní pro zobrazení zpráv od clienta.
Spring podporuje celou řadu technologií (MyBatis, quartz, Hibernate, …) a dokáže s nimi velice dobře
spolupracovat (viz odkazy na konci zadání) - snaž se toho co nejvíce využít. Obě aplikace mohou
běžet na jednom Tomcat serveru.
