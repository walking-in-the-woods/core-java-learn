package BinarySearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinarySearchMain {
    public static void main(String[] args) {
        Theatre theatre = new Theatre("Olympian", 8, 12);
        List<Theatre.Seat> seatCopy = new ArrayList<>(theatre.seats); // shallow copy
        printList(seatCopy);

        seatCopy.get(1).reserve();
        if(theatre.reserveSeat("A02")) {
            System.out.println("Please pay for A02");
        } else {
            System.out.println("Seat already reserved");
        }

        //Collections.reverse(seatCopy); // reverse a list
        Collections.shuffle(seatCopy); // randomize a list
        System.out.println("Printing seatCopy");
        printList(seatCopy);
        System.out.println("Printing theatre.seat");
        printList(theatre.seats);

        Theatre.Seat minSeat = Collections.min(seatCopy);
        Theatre.Seat maxSeat = Collections.max(seatCopy);
        System.out.println("Min seat number is " + minSeat.getSeatNumber());
        System.out.println("Max seat number is " + maxSeat.getSeatNumber());

        sortList(seatCopy);
        System.out.println("Printing sorted seatCopy");
        printList(seatCopy);

        //List<Theatre.Seat> newList = new ArrayList<>(theatre.seats.size());
        //Collections.copy(newList, theatre.seats); // throws exception
    }

    public static void printList(List<Theatre.Seat> list) {
        for(Theatre.Seat seat : list) {
            System.out.print(" " + seat.getSeatNumber());
        }
        System.out.println();
        System.out.println("================================================================================");
    }

    public static void sortList(List<? extends Theatre.Seat> list) {
        for(int i=0; i<list.size()-1; i++) {
            for(int j=0; j<list.size(); j++) {
                if(list.get(i).compareTo(list.get(j)) > 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }

//        theatre.getSeats();

//        if(theatre.reserveSeat("B13")) {
//            System.out.println("Please pay");
//        } else {
//            System.out.println("Sorry, seat is taken");
//        }

//        if(theatre.reserveSeat("H11")) {
//            System.out.println("Please pay");
//        } else {
//            System.out.println("Sorry, seat is taken");
//        }
//   }
}
