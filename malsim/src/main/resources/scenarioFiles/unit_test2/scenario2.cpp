#include <iostream>
#include <vector>
#include <string>
#include <bits/stdc++.h>

using namespace std;

int main(int argc, char** argv)
{
    if (argc < 1) {
        std::cerr << "Must pass either reset or deploy as an argument!";
    }

    if (strcmp(argv[1],"deploy") == 0) {
        fstream file;
        file.open("C:\\cpptest.txt", fstream::out);

        if(!file) 
        { 
            printf("Error in creating file!!!");
            return 1; 
        } 
  
        file << "File created successfully.1234" << endl; 
        file.close();

        return 0;  
   } 

   else if (strcmp(argv[1], "reset") == 0) {
       if (remove("C:\\cpptest.txt") == 0) {
        printf("File Removed");
        return 0;
       } else {
        printf("Unable to Remove File!");
        return 1;
       }
   } else {
       printf("Invalid Argument! Pass either reset or deploy.");
       return 1;
   }
}
