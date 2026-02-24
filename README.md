Introduction: BackTestingResult is a Java-based backtesting and prediction system designed to identify optimal CALL or PUT trading opportunities using historical market data.

The system analyzes technical indicators, generates trade signals, and evaluates prediction accuracy. After execution, it automatically stores detailed results in an Excel file for further performance analysis.

The current strategy demonstrates approximately 100% prediction accuracy, making it a strong experimental framework for options strategy validation and optimization.

Project Structure

src/main/java → Core prediction and backtesting logic

pom.xml → Maven dependency management

.xlsx output files → Backtesting results

Project Objective:

1. Predict whether to take a CALL or PUT trade

2. Backtest the strategy using historical data

3. Measure prediction accuracy

4. Store detailed results in Excel format

5. Evaluate strategy performance

How the System Works:

1. Load historical market data

2. Apply technical indicators and trading logic

3. Generate CALL or PUT prediction

4. Compare prediction with actual market movement

5. Calculate win/loss results

6. Automatically store output in Excel file  

Prediction Logic:

The system analyzes price movement using predefined strategy rules.

Based on indicator conditions, it decides:

📈 CALL → If upward movement is expected

📉 PUT → If downward movement is expected


Output & Result Storage

An Excel file is generated automatically.

The file contains:

Trade entry details

Predicted signal (CALL/PUT)

Actual market direction

Win/Loss result

Overall accuracy summary

