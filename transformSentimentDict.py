def transform_sentiment(input_file, output_file):
    score_to_sentiment = {
        range(-5, -2): "negative",   # -5 to -3
        range(-2, 0): "decrement",     # -2 to -1
        range(0, 1): "revert",        # 0
        range(1, 3): "increment",      # 1 to 2
        range(3, 6): "positive"      # 3 to 5
    }

    with open(input_file, 'r') as infile, open(output_file, 'w') as outfile:
        for line in infile:
            if line.strip():  # Ensure the line is not empty
                word, score_str = line.strip().split(',')
                score = int(score_str)  # Convert score to integer

                # Determine the category for each score
                for score_range, sentiment in score_to_sentiment.items():
                    if score in score_range:
                        transformed_line = f"{word},{sentiment}\n"
                        outfile.write(transformed_line)
                        break

# Example usage
input_file = './sentiment_dict_en_1.txt'
output_file = 'transformed_dict.txt'
transform_sentiment(input_file, output_file)
