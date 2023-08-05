Description<br /> 
TelegramBot is a Java-based chatbot that interacts with users on the Telegram messaging platform. It is designed to perform various tasks, provide information, and respond to user queries. The bot is powered by the Telegram Bot API and uses natural language processing techniques to understand user inputs.<br /> <br /> 

Features<br /> 
Interactive Chat: Adding bot to your chat to control debts with friends or colleages.<br /> 
Custom Commands: Implemented commands for debt, credit and balance. All information stored in DataBase.<br /> 
Inline Queries: Support inline queries to quickly respond to users' requests.<br /> 
Third-party Integration: Connect with external APIs to fetch data and provide more functionalities.<br /> 
User Management: Manage creditline using commands, that @person, so creditor/debtor always get notification about transactions.<br /> <br /> 
Installation<br /> 
Clone the repository:<br /> 
bash<br /> 
Copy code<br /> 
git clone https://github.com/H1EBUWEK/TelegramBot.git<br /> 
Build the project:<br /> 
bash<br /> 
Copy code<br /> 
mvn clean install<br /> 
Configuration<br /> <br /> 
Create a new bot on Telegram:<br /> <br /> 

Open Telegram and search for the "BotFather" bot.<br /> 
Start a chat and create a new bot by following the instructions.<br /> 
Obtain the API token provided by the "BotFather."<br /> 
Create a configuration file:<br /> 

Rename config.example.ini to config.ini.<br /> 
Replace <YOUR_API_TOKEN> with the API token obtained from the "BotFather."<br /> 
Usage<br /> 
Run the bot:<br /> 
bash<br /> 
Copy code<br /> 
java -jar TelegramBot.jar<br /> 
Interact with the bot:<br /> 
Open Telegram and search for the bot created earlier.<br /> 
Start a chat with the bot and explore its functionalities.<br /> 
Contributing<br /> 
Contributions are welcome! If you would like to contribute to this project, please follow these steps:<br /> 
<br /> 
Fork the repository.<br /> 
<br /> 
Create a new branch for your feature or bug fix:<br /> 
<br /> 
bash<br /> 
Copy code<br /> 
git checkout -b feature/your-feature-name<br /> 
Make your changes and commit them:<br /> 
bash<br /> 
Copy code<br /> 
git commit -m "Add your commit message here"<br /> 
Push your changes to your fork:<br /> 
bash<br /> 
Copy code<br /> 
git push origin feature/your-feature-name<br /> 
Submit a pull request to the main repository.<br /> 
