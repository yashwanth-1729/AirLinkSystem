package airlink.sorting;

import airlink.models.Flight;

public class FlightSorting {

    public static void sortByDelay(Flight[] flights) {

        int n = flights.length;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (flights[j].delay > flights[j + 1].delay) {

                    Flight temp = flights[j];
                    flights[j] = flights[j + 1];
                    flights[j + 1] = temp;
                }
            }
        }

        System.out.println("Flights Sorted By Delay:");

        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }
}