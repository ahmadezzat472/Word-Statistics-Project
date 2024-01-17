# Word Statistics Project

## Overview

The Word Statistics project is designed to analyze text files within a specified directory and its subdirectories, providing real-time updates through a graphical user interface (GUI).

## Problem Modeling

- **Main Thread Functionality:**
  - Identifies text files in the specified directory and its subdirectories (up to two levels).
  - Displays identified files in a user-friendly GUI.

- **Threaded Processing:**
  - Employs a thread for each text file, with the number of threads based on the available processors (cores).
  - Each thread processes one or more text files independently.
  - Sends real-time updates to the GUI for a dynamic user experience.

## GUI Features

- **Input:**
  - Accepts the directory path through manual input or a convenient browse button.
  - Provides a checkbox for users to include subdirectories in the analysis.

- **Output (Displayed in Table Form):**
  - Counts of specific words: #words, #is, #are, #you.
  - Longest word per file.
  - Shortest word per file.
  - Longest word per directory.
  - Shortest word per directory.

- **Real-time Updates:**
  - Ensures updates are reflected on the GUI as each thread processes its respective text file(s).

## Instructions for Use

1. Launch the application.
2. Input the desired directory path or use the browse button for selection.
3. Choose whether to include subdirectories by checking the provided checkbox.
4. Real-time updates will be displayed in a table format, offering comprehensive statistics on word occurrences, word lengths, and more.

## Notes

- The project is optimized for performance by utilizing multi-threading based on the number of available processors.
- The GUI provides an intuitive interface for users to interact with and monitor the progress of the word statistics analysis in real time.

Feel free to reach out for further information or assistance!
