# TARS Chatbot User Guide

## Overview
Tars is a command-line chatbot that allows the user to manage their tasks efficiently. It supports adding, listing, marking/unmarking and deleting tasks. It is a lightweight application with a simple UI, geared for productivity. 

## Product Screenshot
![image](https://github.com/user-attachments/assets/d458eb3c-1651-41c1-b355-0f5ef2bc276e)

## Features
### 1. Adding Todos
Users can add basic to-do tasks with a description.

**Usage**
```
/todo {task_description}
```
**Example**
```
/todo Go to the gym
```
**Expected Output**
```
Yes Captain. I've added the following task:
[T][ ] Go to the gym
There are now 1 tasks in your list.
```

### 2. Adding Deadlines
Users can add tasks with deadlines to keep track of their due dates.

**Usage**
```
/deadline {task_description} /by {due_date}
```
**Example**
```
/deadline Submit team report /by Weds 2025-04-30
```
**Expected Output**
```
Yes Captain. I've added the following task:
[D][ ] Submit team report (by: Weds 30 Apr 2025)
There are now 2 tasks in your list.
```

### 3. Adding Events
Users can add events with start and end times.

**Usage**
```
/event {task_description} /from {start_time} /to {end_time}
```
**Example**
```
/event Meeting with the team /from tomorrow 1400 /to 1600
```
**Expected Output**
```
Yes Captain. I've added the following task:
[E][ ] Meeting with the team (from: 2.00 pm to: 4.00pm)
There are now 3 tasks in your list.
```

### 4. Listing All Tasks
Users can view all tasks in their list

**Usage**
```
/list
```
**Expected Output**
```
You have X task(s) in your list:
1. [T][ ] Go to the gym
2. [D][ ] Submit team report (by: Weds 30 Apr 2025)
3. [E][ ] Meeting with the team (from: 2.00 pm to: 4.00pm)
```
### 5. Marking Tasks as Done
Users can mark tasks as completed.

**Usage**
```
/mark {task_number}
```
**Example**
```
/mark 1
```
**Expected Output**
```
Task Done. Good Job Captain!
[T][X] Go to the gym
```

### 6. Unmarking Tasks
Users can mark tasks as incomplete.

**Usage**
```
/unmark {task_number}
```
**Example**
```
/unmark 1
```
**Expected Output**
```
Ok, your task is marked as not done yet.
[T][ ] Go to the gym
```

### 7. Deleting tasks
Users can remove tasks from the list.

**Usage**
```
/delete {task_number}
```
**Example**
```
/delete 1
```
**Expected Output**
```
Roger Captain/ I've deleted this task.
[T][ ] Go to the gym
```
### 8. Finding tasks
Users can remove tasks from the list.

**Usage**
```
/find {search_term}
```
**Example**
```
/find team
```
**Expected Output**
```
Here are the tasks matching the search term: "team"
2. [D][ ] Submit team report (by: Weds 30 Apr 2025)
3. [E][ ] Meeting with the team (from: 2.00 pm to: 4.00pm)
```
### 9. Exiting the Chatbot
Users can exit the chatbot

**Usage**
```
/bye
```
**Expected Output**
```
Goodnight Captain. Sleep well.
```
