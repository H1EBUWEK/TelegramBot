Description
TelegramBot is a Java-based chatbot that interacts with users on the Telegram messaging platform. It is designed to perform various tasks, provide information, and respond to user queries. The bot is powered by the Telegram Bot API and uses natural language processing techniques to understand user inputs.

Features
Interactive Chat: Adding bot to your chat to control debts with friends or colleages./n
Custom Commands: Implemented commands for debt, credit and balance. All information stored in DataBase.
Inline Queries: Support inline queries to quickly respond to users' requests.
Third-party Integration: Connect with external APIs to fetch data and provide more functionalities.
User Management: Manage creditline using commands, that @person, so creditor/debtor always get notification about transactions.
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/H1EBUWEK/TelegramBot.git
Build the project:
bash
Copy code
mvn clean install
Configuration
Create a new bot on Telegram:

Open Telegram and search for the "BotFather" bot.
Start a chat and create a new bot by following the instructions.
Obtain the API token provided by the "BotFather."
Create a configuration file:

Rename config.example.ini to config.ini.
Replace <YOUR_API_TOKEN> with the API token obtained from the "BotFather."
Usage
Run the bot:
bash
Copy code
java -jar TelegramBot.jar
Interact with the bot:
Open Telegram and search for the bot created earlier.
Start a chat with the bot and explore its functionalities.
Contributing
Contributions are welcome! If you would like to contribute to this project, please follow these steps:

Fork the repository.

Create a new branch for your feature or bug fix:

bash
Copy code
git checkout -b feature/your-feature-name
Make your changes and commit them:
bash
Copy code
git commit -m "Add your commit message here"
Push your changes to your fork:
bash
Copy code
git push origin feature/your-feature-name
Submit a pull request to the main repository.
