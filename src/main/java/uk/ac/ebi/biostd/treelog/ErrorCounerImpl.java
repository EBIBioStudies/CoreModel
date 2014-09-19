package uk.ac.ebi.biostd.treelog;

public class ErrorCounerImpl implements ErrorCounter
{
 private int counter=0;

 @Override
 public int getErrorCounter()
 {
  return counter;
 }

 @Override
 public void incErrorCounter()
 {
  counter++;
 }

 @Override
 public void addErrorCounter(int countErrors)
 {
  counter += countErrors;
 }

 @Override
 public void resetErrorCounter()
 {
  counter = 0;
 }

}
