    import java.util.Random;
class Sim {

// Class Sim variables
public static double Clock, MeanInterArrivalTime, MeanServiceTime, SIGMA, LastEventTime,
        TotalBusy, MaxQueueLength, SumResponseTime, TotalInterArrivalTime, TotalServiceTime;

public static long  NumberOfCustomers, QueueLength, NumberInService,
        TotalCustomers, NumberOfDepartures, LongService;

public final static int arrival = 1;
public final static int departure = 2;

public static EventList FutureEventList;
public static Queue Customers;
public static Random stream;

public static void main(String argv[]) {

  MeanInterArrivalTime = 2.5; MeanServiceTime = 2;
  SIGMA                = 0.6; TotalCustomers  = 50000;
  long seed            = Long.parseLong(argv[0]);

  stream = new Random(seed);           // initialize rng stream
  FutureEventList = new EventList();
  Customers = new Queue();
 
  Initialization();

  // Loop until first "TotalCustomers" have departed
  while(NumberOfDepartures < TotalCustomers ) {
    Event evt = (Event)FutureEventList.getMin();  // get imminent event
    FutureEventList.dequeue(); // be rid of it
    Clock = evt.get_time();                       // advance simulation time
    if( evt.get_type() == arrival ) ProcessArrival(evt);
    else  ProcessDeparture(evt);
    }
  ReportGeneration();
 }

 // seed the event list with TotalCustomers arrivals
 public static void Initialization()   { 
  Clock = 0.0;
  QueueLength = 0;
  NumberInService = 0;
  LastEventTime = 0.0;
  TotalBusy = 0 ;
  MaxQueueLength = 0;
  SumResponseTime = 0;
  NumberOfDepartures = 0;
  LongService = 0;
  TotalInterArrivalTime=0.0;
  TotalServiceTime=0.0;
 

  // create first arrival event
  Event evt = new Event(arrival, exponential( stream, MeanInterArrivalTime));
  FutureEventList.enqueue( evt );
 }

 public static void ProcessArrival(Event evt) {
  Customers.enqueue(evt); 
  QueueLength++;
  // if the server is idle, fetch the event, do statistics
  // and put into service
  if( NumberInService == 0) ScheduleDeparture();
  else TotalBusy += (Clock - LastEventTime);  // server is busy

  // adjust max queue length statistics
  if (MaxQueueLength < QueueLength) MaxQueueLength = QueueLength;

  // schedule the next arrival
  Event next_arrival = new Event(arrival, Clock+exponential(stream, MeanInterArrivalTime));
  FutureEventList.enqueue( next_arrival );
  LastEventTime = Clock;
  TotalInterArrivalTime += next_arrival.get_time()-Clock;  /// I changed.
 }

 public static void ScheduleDeparture() {
  double ServiceTime;
  // get the job at the head of the queue
  while (( ServiceTime = exponential(stream, MeanServiceTime)) < 0 );
  Event depart = new Event(departure,Clock+ServiceTime);
  FutureEventList.enqueue( depart );
  NumberInService = 1;
  QueueLength--;
  TotalServiceTime += ServiceTime;
 }

public static void ProcessDeparture(Event e) {
 // get the customer description
 Event finished = (Event) Customers.dequeue();
 // if there are customers in the queue then schedule
 // the departure of the next one
  if( QueueLength > 0 ) ScheduleDeparture();
  else NumberInService = 0;
  // measure the response time and add to the sum
  double response = (Clock - finished.get_time());
  SumResponseTime += response;
  if( response > 4.0 ) LongService++; // record long service
  TotalBusy += (Clock - LastEventTime );
  NumberOfDepartures++;
  LastEventTime = Clock;
 }

public static void ReportGeneration() {
double RHO   = TotalBusy/Clock;
double AVGR  = SumResponseTime/TotalCustomers;
double PC4   = ((double)LongService)/TotalCustomers;
double ArrivalTime = TotalInterArrivalTime/TotalCustomers;
double ServiceTime = TotalServiceTime/TotalCustomers;
double W_System= 1/((1/ServiceTime) - (1/ArrivalTime));
double W_Queue= RHO*W_System;
double L_System= RHO/(1-RHO);
double L_Queue= (RHO*RHO)/(1-RHO);


System.out.println( "SINGLE SERVER QUEUE SIMULATION - SECURITY CHECKOUT COUNTER ");
System.out.println( "\tMEAN INTERARRIVAL TIME                         " + MeanInterArrivalTime );
System.out.println( "\tMEAN SERVICE TIME                              " + MeanServiceTime );

System.out.println( "\tAvg Inter Arrival Time                         " + ArrivalTime);
System.out.println( "\tAvg Service Time                               " + ServiceTime);

System.out.println( "\tNUMBER OF CUSTOMERS SERVED                     " + TotalCustomers );
System.out.println(); 
System.out.println( "\tSERVER UTILIZATION                             " + RHO );

System.out.println( "\tNUMBER OF DEPARTURES                           " + TotalCustomers );


System.out.println( "\tAvg Time Spent in System per Customer          " + W_System);
System.out.println( "\tAvg Time Spent in Queue per Customer           " + W_Queue);
System.out.println( "\tAvg Number of Customer in System               " + L_System);
System.out.println( "\tAvg Number of Customer in Queue                " + L_Queue);
}

public static double exponential(Random rng, double mean) {
 return -mean*Math.log( rng.nextDouble() );
}


}
