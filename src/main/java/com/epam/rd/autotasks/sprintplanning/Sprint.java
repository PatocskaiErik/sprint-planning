package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

import java.util.Arrays;

public class Sprint {
    private final int capacity;
    private final int ticketsLimit;
    private int estimatedTime = 0;
    private int numberOfTickets = 0;
    private final Ticket[] tickets;

    public Sprint(int capacity, int ticketsLimit) {
        this.capacity = capacity;
        this.ticketsLimit = ticketsLimit;
        this.tickets = new Ticket[ticketsLimit];
    }

    public boolean addUserStory(UserStory userStory) {
        if (userStory == null || numberOfTickets == ticketsLimit || userStory.getEstimate() + estimatedTime > capacity || userStory.isCompleted())
            return false;
        if (userStory.getDependencies() != null && userStory.getDependencies().length != 0)
            for (UserStory i : userStory.getDependencies()) {
                if (!ifStoryInSprint(i))
                    return false;
            }

        estimatedTime += userStory.getEstimate();
        tickets[numberOfTickets++] = userStory;
        return true;
    }

    public boolean addBug(Bug bugReport) {
        if (bugReport == null || numberOfTickets == ticketsLimit || bugReport.getEstimate() + estimatedTime > capacity || bugReport.isCompleted())
            return false;

        estimatedTime +=bugReport.getEstimate();
        tickets[numberOfTickets++]=bugReport;
        return true;
    }

    public Ticket[] getTickets() {
        return Arrays.copyOf(tickets,numberOfTickets);
    }

    public int getTotalEstimate() {
        return estimatedTime;
    }

    private boolean ifStoryInSprint (UserStory userstory) {
        for (Ticket i : tickets) {
            if (userstory.equals(i))
                return true;
        }
        return false;
    }
}
