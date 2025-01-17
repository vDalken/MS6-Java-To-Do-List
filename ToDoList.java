import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class ToDoList {
    static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean premiumPlan = false;
        String[] toDoList;
        ArrayList<String> deletedTasks = new ArrayList<>();
        if (!premiumPlan) {
            System.out.println("\n\t\t\t\t\u001b[43;1m\u001b[38;5;15mIMPORTANT WARNING\u001b[0m\u001b[38;5;11m\nYou are currently using the Free Plan of ToDoList!\nYou can upgrade to Premium Plan in the upgrade menu!\u001b[0m");
            toDoList = new String[10];
        } else {
            toDoList = new String[30];
        }

        String userChoice;

        do {

            int count = 0;
            for (int i = 0; i < toDoList.length; i++) {
                if (toDoList[i] == null) {
                    count++;
                }
            }

            System.out.println("\n\u001b[38;5;15mYou still have \u001b[38;5;11m" + count + "\u001b[38;5;15m free spaces on the list!\u001b[0m");

//            System.out.println("\n\u001b[38;5;15m " + Arrays.toString(toDoList) + "\u001b[0m");

            System.out.println("\u001b[38;5;15m0 - Exit ToDoList\u001b[0m");
            System.out.println("\u001b[38;5;15m1 - Show ToDoList\u001b[0m");
            System.out.println("\u001b[38;5;15m2 - Create task\u001b[0m");
            System.out.println("\u001b[38;5;15m3 - Mark as completed\u001b[0m");
            System.out.println("\u001b[38;5;15m4 - Remove as completed\u001b[0m");
            System.out.println("\u001b[38;5;15m5 - Edit task\u001b[0m");
            System.out.println("\u001b[38;5;15m6 - Delete task\u001b[0m");
            System.out.println("\u001b[38;5;15m7 - Organize alphabetically\u001b[0m");
            System.out.println("\u001b[38;5;15m8 - Upgrade ToDoList Plan\u001b[0m");
            System.out.println("\u001b[38;5;15m9 - Organize by completed and uncompleted\u001b[0m");
            System.out.println("\u001b[38;5;15m10 - Remove all tasks set as completed\u001b[0m");
            System.out.println("\u001b[38;5;15m11 - Recover deleted tasks\u001b[0m");
            System.out.println("\u001b[38;5;15m12 - Add task note\u001b[0m");
            System.out.print("\u001b[38;5;15mChoose an option: \u001b[0m");
            userChoice = scan.next();

            scan.nextLine();


            switch (userChoice) {
                case "1":
                    showToDoList(toDoList, "ToDoList");
                    break;
                case "2":
                    createTask(toDoList);
                    break;
                case "3":
                    markTaskAsCompleted(toDoList);
                    break;
                case "4":
                    removeTaskAsCompleted(toDoList);
                    break;
                case "5":
                    editTask(toDoList);
                    break;
                case "6":
                    deleteTask(toDoList, deletedTasks);
                    break;
                case "7":
                    organizeAlphabetically(toDoList);
                    break;
                case "8":
                    premiumPlan = upgradeToDoListPlan(toDoList, premiumPlan);
                    if (premiumPlan) {
                        String[] tempToDoList = new String[30];

                        for (int i = 0; i < toDoList.length; i++) {
                            if (i < tempToDoList.length) {
                                tempToDoList[i] = toDoList[i];
                            } else {
                                break;
                            }
                        }

                        toDoList = tempToDoList;
                    }
                    break;
                case "9":
                    organizeByDoneAndUndone(toDoList);
                    break;
                case "10":
                    removeAllTasksSetAsCompleted(toDoList, deletedTasks);
                    break;
                case "11":
                    recoverDeletedTasks(toDoList, deletedTasks);
                    break;
                case "12":
                    addTaskNote(toDoList);
                    break;
                case "0":
                    System.out.println("\n\u001b[38;5;9mClosing ToDoList program...\u001b[0m");
                    break;
                default:
                    System.out.println("\n\u001b[38;5;9mInvalid option!\u001b[0m");
                    break;
            }

        } while (!userChoice.equals("0"));
    }

    public static void showToDoList(String[] toDoList, String title) {

        int count = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                count++;
            }
        }

        if (count > 0) {
            System.out.println("\n\t\t\u001b[38;5;15m" + title + "\u001b[0m");
            System.out.println("\u001b[38;5;8m------------------------\u001b[0m");
            int counter = 0;
            for (int i = 0; i < toDoList.length; i++) {
                if (toDoList[i] != null) {
                    if (toDoList[i].contains(" ✅")) {
                        System.out.println("\u001b[38;5;7m" + (i + 1) + ". \u001b[38;5;40m" + toDoList[i] + "\u001b[0m");
                        counter++;
                    } else {
                        System.out.println("\u001b[38;5;7m" + (i + 1) + ". \u001b[38;5;1m" + toDoList[i] + "\u001b[0m");
                        counter++;
                    }
                }
            }
            if (taskCountDisplay(toDoList) == 1) {
                System.out.println("\nThis To Do List has " + taskCountDisplay(toDoList) + " task!\n");
            } else {
                System.out.println("\nThis To Do List has " + taskCountDisplay(toDoList) + " tasks!\n");
            }
            displayTaskCompletionPercentage(toDoList);
            System.out.println("\u001b[38;5;8m------------------------\u001b[0m");
        } else {
            System.out.println("\n\u001b[38;5;9mThe ToDoList is empty! You should create a task first.\u001b[0m");
        }
    }

    public static void showEliminatedTasks(ArrayList<String> deletedTasks, String title) {
        if (!deletedTasks.isEmpty()) {
            System.out.println("\n\t\t\u001b[38;5;15m" + title + "\u001b[0m");
            System.out.println("\u001b[38;5;8m------------------------------\u001b[0m");
            for (int i = 0; i < deletedTasks.size(); i++) {
                if (deletedTasks.get(i) != null) {
                    if (deletedTasks.get(i).contains(" ✅")) {
                        System.out.println("\u001b[38;5;7m" + (i + 1) + ". \u001b[38;5;40m" + deletedTasks.get(i) + "\u001b[0m");
                    } else {
                        System.out.println("\u001b[38;5;7m" + (i + 1) + ". \u001b[38;5;1m" + deletedTasks.get(i) + "\u001b[0m");
                    }
                }
            }
            System.out.println("\nThis To Do List has " + deletedTasks.size() + " tasks!\n");
            System.out.println("\u001b[38;5;8m------------------------------\u001b[0m");
        } else {
            System.out.println("\n\u001b[38;5;9mThe ToDoList is empty! You should create a task first.\u001b[0m");
        }
    }

    public static void createTask(String[] toDoList) {
        Scanner scan = new Scanner(System.in);

        System.out.print("\n\u001b[38;5;15mType in the task name: \u001b[0m");
        String userNewTask = scan.nextLine().trim();
        //System.out.println("\n\u001b[38;5;15mType in the task note/type nothing so it doesn't add a note: \u001b[0m");
        //String note = scan.nextLine().trim();

        if (!userNewTask.isEmpty()) {
            boolean added = false;

            for (int i = 0; i < toDoList.length; i++) {
                if (toDoList[i] == null) {
                    //toDoList[i] = userNewTask;
                    System.out.println("\n\u001b[38;5;10mThe task '\u001b[38;5;15m" + userNewTask + "\u001b[38;5;10m' was created!\u001b[0m");
                    LocalDateTime myDateObj = LocalDateTime.now();
                    String formattedDate = myDateObj.format(myFormatObj);
                    toDoList[i] = userNewTask + " \uD83D\uDD70 " + formattedDate;
                    added = true;
                    break;
                }
            }

            if (!added) {
                System.out.println("\n\n\u001b[38;5;9mThe list is full! You don't have more space.\u001b[0m");
            }
        } else {
            System.out.println("\n\n\u001b[38;5;9mTask name cannot be empty.\u001b[0m");
        }
    }

    public static void markTaskAsCompleted(String[] toDoList) {
        Scanner scan = new Scanner(System.in);

        int count = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                count++;
            }
        }
        showToDoList(toDoList, "ToDoList");
        if (count != 0) {
            System.out.print("\n\u001b[38;5;15mChoose a task to mark as completed: \u001b[0m");
            String userChoiceOfTaskToMarkAsCompleted = scan.next();

            if (isANumber(userChoiceOfTaskToMarkAsCompleted.trim())) {
                int userChoiceOfTaskToMarkAsCompletedInt = (Integer.parseInt(userChoiceOfTaskToMarkAsCompleted)) - 1;

                if (userChoiceOfTaskToMarkAsCompletedInt >= 0 && userChoiceOfTaskToMarkAsCompletedInt <= toDoList.length) {
                    if (toDoList[userChoiceOfTaskToMarkAsCompletedInt] != null) {
                        if (toDoList[userChoiceOfTaskToMarkAsCompletedInt].contains(" ✅")) {
                            System.out.println("\n\u001b[38;5;9mThat task is already marked as completed!\u001b[0m");
                        } else {
                            toDoList[userChoiceOfTaskToMarkAsCompletedInt] = replaceTimeOfDeletedTask(toDoList[userChoiceOfTaskToMarkAsCompletedInt]);
                            toDoList[userChoiceOfTaskToMarkAsCompletedInt] = toDoList[userChoiceOfTaskToMarkAsCompletedInt].concat(" ✅");
                            System.out.println("\n\u001b[38;5;10mTask successfuly marked as completed!\u001b[0m");
                        }
                    } else {
                        System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                    }
                } else {
                    System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write one number!\u001b[0m");
            }
        }
    }

    public static void removeTaskAsCompleted(String[] toDoList) {
        Scanner scan = new Scanner(System.in);
        String[] toDoListCompletedTasks = new String[toDoList.length];
        int existsCompletedTasks = 0;
        int counter = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null && toDoList[i].contains(" ✅")) {
                toDoListCompletedTasks[counter] = toDoList[i];
                counter++;
                existsCompletedTasks++;
            }
        }
        if (counter == 0) {
            System.out.println("\n\u001b[38;5;9mThere are no completed tasks to remove!\u001b[0m");
        } else {
            showToDoList(toDoListCompletedTasks, "Completed ToDoList Tasks");
        }

        if (existsCompletedTasks > 0) {

            System.out.print("\n\u001b[38;5;15mChoose a task to remove as completed: \u001b[0m");
            String userChoiceOfTaskToRemoveAsCompleted = scan.next();
            if (isANumber(userChoiceOfTaskToRemoveAsCompleted)) {
                int userChoiceOfTaskToRemoveAsCompletedInt = (Integer.parseInt(userChoiceOfTaskToRemoveAsCompleted)) - 1;

                if (userChoiceOfTaskToRemoveAsCompletedInt >= 0 && userChoiceOfTaskToRemoveAsCompletedInt <= toDoList.length) {
                    if (toDoList[userChoiceOfTaskToRemoveAsCompletedInt] != null) {
                        toDoList[userChoiceOfTaskToRemoveAsCompletedInt] = replaceTimeOfDeletedTask(toDoList[userChoiceOfTaskToRemoveAsCompletedInt]);
                        toDoList[userChoiceOfTaskToRemoveAsCompletedInt] = toDoList[userChoiceOfTaskToRemoveAsCompletedInt].replace(" ✅", "");
                        System.out.println("\n\u001b[38;5;10mTask successfuly removed as completed!\u001b[0m");
                    } else {
                        System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                    }
                } else {
                    System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write one number!\u001b[0m");
            }
        }
    }

    public static void editTask(String[] toDoList) {
        Scanner scan = new Scanner(System.in);

        int count = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                count++;
            }
        }

        showToDoList(toDoList, "ToDoList");

        if (count != 0) {
            System.out.print("\n\u001b[38;5;15mChoose a task to edit: \u001b[0m");
            String userChoiceOfTaskToEdit = scan.next();
            scan.nextLine();
            if (isANumber(userChoiceOfTaskToEdit)) {
                int userChoiceOfTaskToEditInt = (Integer.parseInt(userChoiceOfTaskToEdit)) - 1;
                if (userChoiceOfTaskToEditInt >= 0 && userChoiceOfTaskToEditInt <= toDoList.length) {
                    if (toDoList[userChoiceOfTaskToEditInt] != null) {
                        System.out.println("\n\u001b[38;5;15mOld: " + toDoList[userChoiceOfTaskToEditInt] + "\u001b[0m");
                        System.out.print("\u001b[38;5;15mNew: \u001b[0m");
                        String userEditTask = scan.nextLine();
                        int indexOfClock = toDoList[userChoiceOfTaskToEditInt].indexOf("\uD83D\uDD70");
                        String oldTask = toDoList[userChoiceOfTaskToEditInt].substring(0, indexOfClock - 1);
                        toDoList[userChoiceOfTaskToEditInt] = toDoList[userChoiceOfTaskToEditInt].replace(oldTask, userEditTask.trim());
                        toDoList[userChoiceOfTaskToEditInt] = replaceTimeOfDeletedTask(toDoList[userChoiceOfTaskToEditInt]);

                        System.out.println("\n\u001b[38;5;10mThe task '\u001b[38;5;15m" + oldTask + "\u001b[38;5;10m' was changed to '\u001b[38;5;15m" + userEditTask.trim() + "\u001b[38;5;10m'!");
                    } else {
                        System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                    }
                } else {
                    System.out.println("\n\u001b[38;5;9mInvalid task option!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write one number!\u001b[0m");
            }
        } else {
            System.out.println("\n\u001b[38;5;9mYou don't have tasks to edit!\u001b[0m");
        }
    }

    public static void deleteTask(String[] toDoList, ArrayList<String> deletedTasks) {
        Scanner scan = new Scanner(System.in);

        int existedTasks = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                existedTasks++;
            }
        }
        showToDoList(toDoList, "ToDoList");

        if (existedTasks > 0) {
            System.out.print("\n\u001b[38;5;15mChoose a task to delete: \u001b[0m");
            String userChoiceOfTaskToDelete = scan.next();
            if (isANumber(userChoiceOfTaskToDelete)) {
                int userChoiceOfTaskToDeleteInt = (Integer.parseInt(userChoiceOfTaskToDelete)) - 1;

                if (userChoiceOfTaskToDeleteInt >= 0 && userChoiceOfTaskToDeleteInt <= toDoList.length) {
                    if (toDoList[userChoiceOfTaskToDeleteInt] != null) {
                        toDoList[userChoiceOfTaskToDeleteInt] = replaceTimeOfDeletedTask(toDoList[userChoiceOfTaskToDeleteInt]);
                        System.out.println("\u001b[38;5;10mThe task '\u001b[38;5;15m" + getTaskName(toDoList[userChoiceOfTaskToDeleteInt]) + "\u001b[38;5;10m' was successfully deleted!\u001b[0m");
                        deletedTasks.add(toDoList[userChoiceOfTaskToDeleteInt]);
                        toDoList[userChoiceOfTaskToDeleteInt] = null;
                    } else {
                        System.out.println("\u001b[38;5;9mInvalid task option!\u001b[0m");
                    }
                } else {
                    System.out.println("\u001b[38;5;9mInvalid task option!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write one number!\u001b[0m");
            }
        }

        organizeList(toDoList);
    }

    public static void organizeAlphabetically(String[] toDoList) {
        int count = 0;
        String[] toDoListCopy1 = new String[toDoList.length];
        System.arraycopy(toDoList, 0, toDoListCopy1, 0, toDoListCopy1.length);
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                count++;
            }
        }
        Arrays.sort(toDoList, 0, count);
        boolean isAlreadyOrganized = false;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                if (getTaskName(toDoList[i]).equals(getTaskName(toDoListCopy1[i]))) {
                    isAlreadyOrganized = true;
                } else {
                    isAlreadyOrganized = false;
                    break;
                }
            }
        }
        if (isAlreadyOrganized) {
            System.out.println("\n\u001b[38;5;9mThe ToDoList is already organized alphabetically!\u001b[0m");
        } else {
            showToDoList(toDoList, "Updated ToDoList");
        }
    }

    public static boolean upgradeToDoListPlan(String[] toDoList, boolean premium) {
        Scanner scan = new Scanner(System.in);
        if (!premium) {
            System.out.println("\n\u001b[38;5;15mDo you want to buy Premium Plan? (yes or no)\u001b[0m");
            System.out.print("\u001b[38;5;15m> \u001b[0m");
            String userUpgradeOption = scan.next();

            switch (userUpgradeOption) {
                case "yes":
                    premium = true;
                    System.out.println("\n\u001b[38;5;10mCurrently plan setted to Premium! Thank you!\u001b[0m");
                    break;
                default:
                    premium = false;
                    System.out.println("\n\u001b[38;5;12mMaybe next time then...\u001b[0m");
                    break;
            }

            return premium;
        } else {
            System.out.println("\n\u001b[38;5;11mYour plan is already setted to Premium! You don't need to buy it again.\u001b[0m");
        }
        return premium;
    }

    public static void organizeByDoneAndUndone(String[] toDoList) {
        String[] toDoListCopy1 = new String[toDoList.length];
        String[] toDoListCopy2 = new String[toDoList.length];
        String[] toDoListCopy3 = new String[toDoList.length];
        System.arraycopy(toDoList, 0, toDoListCopy1, 0, toDoListCopy1.length);
        System.arraycopy(toDoList, 0, toDoListCopy2, 0, toDoListCopy1.length);
        System.arraycopy(toDoList, 0, toDoListCopy3, 0, toDoListCopy1.length);
        int counter = 0;
        for (int i = 0; i < toDoListCopy1.length; i++) {
            if (toDoListCopy1[i] != null) {
                if (toDoListCopy1[i].contains(" ✅")) {
                    toDoList[counter] = toDoListCopy1[i];
                    counter++;
                }
            }
        }

        for (int i = 0; i < toDoListCopy2.length; i++) {
            if (toDoListCopy2[i] != null) {
                if (!toDoListCopy2[i].contains(" ✅")) {
                    toDoList[counter] = toDoListCopy2[i];
                    counter++;
                }
            }
        }
        boolean isAlreadyOrganized = false;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                if (getTaskName(toDoList[i]).equals(getTaskName(toDoListCopy3[i])) && toDoList[i].contains(" ✅") && toDoListCopy3[i].contains(" ✅")) {
                    isAlreadyOrganized = true;
                } else if (getTaskName(toDoList[i]).equals(getTaskName(toDoListCopy3[i])) && !toDoList[i].contains(" ✅") && !toDoListCopy3[i].contains(" ✅")) {
                    isAlreadyOrganized = true;
                } else {
                    isAlreadyOrganized = false;
                    break;
                }
            }
        }
        if (isAlreadyOrganized) {
            System.out.println("\n\u001b[38;5;9mThe ToDoList is already organized by completed and uncompleted!\u001b[0m");
        } else {
            showToDoList(toDoList, "Updated ToDoList");
        }
    }

    public static void removeAllTasksSetAsCompleted(String[] toDoList, ArrayList<String> deletedTasks) {
        int counter = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                if (toDoList[i].contains(" ✅")) {
                    toDoList[i] = replaceTimeOfDeletedTask(toDoList[i]);
                    deletedTasks.add(toDoList[i]);
                    toDoList[i] = null;
                    counter++;
                }
            }
        }
        if (counter < 1) {
            System.out.println("\n\u001b[38;5;9mThere wasn't any task set as completed to remove!\u001b[0m");
        } else {
            System.out.println("\n\u001b[38;5;10mAll tasks set as completed were removed!\u001b[0m\n");
            showToDoList(toDoList, "Updated ToDoList");
        }
        organizeList(toDoList);
    }

    public static void recoverDeletedTasks(String[] toDoList, ArrayList<String> deletedTasks) {
        showEliminatedTasks(deletedTasks, "Eliminated Tasks");
        if (!deletedTasks.isEmpty()) {
            System.out.print("\nChoose the one you want to recover: ");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.next();
            if (isANumber(userInput)) {
                int userInputInt = (Integer.parseInt(userInput)) - 1;
                if (userInputInt >= 0 && userInputInt < deletedTasks.size()) {
                    for (int i = 0; i < toDoList.length; i++) {
                        if (toDoList[i] == null) {
                            toDoList[i] = replaceTimeOfDeletedTask(deletedTasks.get(userInputInt));
                            deletedTasks.remove(userInput);
                            break;
                        }
                    }
                    System.out.println("\n\u001b[38;5;10mTask was sucessfully recovered!\u001b[0m\n");
                } else {
                    System.out.println("\n\u001b[38;5;9mInvalid option!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write a number!\u001b[0m");
            }
        }
    }

    public static int taskCountDisplay(String[] toDoList) {
        int counter = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    public static void addTaskNote(String[] toDoList) {
        Scanner scanner = new Scanner(System.in);
        showToDoList(toDoList, "ToDoList");
        boolean isThereTasks = false;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                isThereTasks = true;
                break;
            }
        }
        if (isThereTasks) {
            System.out.print("\nTell me the number of the task you want to add the note to: ");
            String numberOfTask = scanner.next();
            if (isANumber(numberOfTask)) {
                int numberOfTaskInt = (Integer.parseInt(numberOfTask)) - 1;
                boolean isNull = toDoList[numberOfTaskInt] == null;
                boolean isThereANote = toDoList[numberOfTaskInt].contains(" \uD83D\uDCDD");
                System.out.println(isThereANote);
                if (!isThereANote) {
                    if (!isNull) {
                        System.out.print("\nTell me the note you would like to add: ");
                        scanner.nextLine();
                        String note = scanner.nextLine();
                        if (toDoList[numberOfTaskInt].contains(" ✅")) {
                            toDoList[numberOfTaskInt] = toDoList[numberOfTaskInt].substring(0, toDoList[numberOfTaskInt].length() - 2);
                            toDoList[numberOfTaskInt] = toDoList[numberOfTaskInt].concat(" \uD83D\uDCDD" + note.trim());
                            toDoList[numberOfTaskInt] = toDoList[numberOfTaskInt].concat(" ✅");
                            System.out.println("\n\u001b[38;5;10mNote was sucessfully added!\u001b[0m");
                        } else {
                            toDoList[numberOfTaskInt] = toDoList[numberOfTaskInt].concat(" \uD83D\uDCDD " + note.trim());
                            System.out.println("\n\u001b[38;5;10mNote was sucessfully added!\u001b[0m");
                        }
                    } else {
                        System.out.println("\n\u001b[38;5;9mThat task doesn't exist!\u001b[0m");
                    }
                }else{
                    System.out.println("\n\u001b[38;5;9mThat task already has a note!\u001b[0m");
                }
            } else {
                System.out.println("\n\u001b[38;5;9mPlease write one number!\u001b[0m");
            }
        } else {
            System.out.println("\n\u001b[38;5;9mThere !\u001b[0m");
        }
    }

    public static void displayTaskCompletionPercentage(String[] toDoList) {
        double completedCounter = 0;
        double toDoListLength = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                if (toDoList[i].contains(" ✅")) {
                    completedCounter += 1;
                }
                toDoListLength += 1;
            }
        }
        double taskCompletionPercentage = (completedCounter / toDoListLength) * 100;
        if ((int) taskCompletionPercentage != 100) {
            System.out.println("The task completion percentage is " + (int) taskCompletionPercentage + "%!\n");
        }
    }

    public static String replaceTimeOfDeletedTask(String taskToBeDeleted) {
        int indexOfClock = taskToBeDeleted.indexOf("\uD83D\uDD70");
        String time = taskToBeDeleted.substring(indexOfClock + 3, indexOfClock + 19);
        LocalDateTime myDateObj = LocalDateTime.now();
        String formattedDate = myDateObj.format(myFormatObj);
        taskToBeDeleted = taskToBeDeleted.replace(time, formattedDate);
        return taskToBeDeleted;
    }

    public static boolean isANumber(String userInput) {
        if (userInput.matches("[0-9]") && !(userInput.matches("[^0-9]"))) {
            return true;
        }
        return false;
    }

    public static void organizeList(String[] toDoList) {
        String[] toDoListCopy = new String[toDoList.length];
        int counter = 0;
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] != null) {
                toDoListCopy[counter] = toDoList[i];
                counter++;
            }
        }
        System.arraycopy(toDoListCopy, 0, toDoList, 0, toDoListCopy.length);
    }

    public static String getTaskName(String task) {
        int indexOfClock = task.indexOf("\uD83D\uDD70");
        return task.substring(0, indexOfClock - 1);
    }
}