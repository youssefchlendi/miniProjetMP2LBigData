# Mini projet big data

This project is a part of my curriculum in Big Data as MP2L student, the goal is to analyze the sentiment of social networks, in this case, we will use Twitter as a source of data. The project will be divided into 3 parts:
- Treatment and analysis of tweets in order to determine the sentiment (positive, negative, neutral) towards specific subjects or entities.
- Implementation of text processing techniques such as tokenization, cleaning and lemmatization.
- Use of sentiment analysis libraries such as TextBlob or NLTK.

## Resources
- [Sentiment140 dataset with 1.6 million tweets](https://www.kaggle.com/datasets/kazanova/sentiment140) (Or the archive zip in the data folder)
- [TextBlob](https://textblob.readthedocs.io/en/dev/)
- [NLTK](https://www.nltk.org/)

## Steps to follow for the project

1. Data collection 
Find a dataset of tweets, we will use the Sentiment140 dataset with 1.6 million tweets. The dataset is available on Kaggle

2. Data preprocessing
The dataset contains 6 columns: target, ids, date, flag, user, text. We will only use the target and text columns. The target column contains the sentiment of the tweet (0 = negative, 2 = neutral, 4 = positive). The text column contains the tweet. We will remove the other columns and rename the target column to sentiment.

3. Data analysis
We will analyze the data to understand the distribution of the sentiments in the dataset.

4. Text processing
We will implement text processing techniques such as
  - Tokenization : Split the text into words or sentences, this will help us to analyze the text.
  - Cleaning : Remove the noise from the text, this includes removing special characters, punctuation, and stop words.
  - Lemmatization : Convert the words into their base form, this will help us to analyze the text.

5. Sentiment analysis
We will use sentiment analysis libraries such as TextBlob or NLTK to analyze the sentiment of the tweets.
