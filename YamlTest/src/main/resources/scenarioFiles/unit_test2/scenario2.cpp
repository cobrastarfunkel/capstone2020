#include <iostream>
#include <vector>
#include <string>
#include <bits/stdc++.h>

using namespace std;

int main()
{
   fstream file;
   file.open("C:\\eclipse\\test_files\\cpptest.txt", fstream::out);

   if(!file) 
   { 
       cout<<"Error in creating file!!!"; 
       return 0; 
   } 
  
   file << "File created successfully.1234" << endl; 
   file.close();

   return 0;   
}