#include "logger.h"

#include <time.h>

#include <fstream>
#include <sstream>

Logger::Logger(const std::string& path)
	: path_(path) {
}

Logger::~Logger() {
}

void Logger::Log(const std::string& text) {
	std::ofstream log_file(path_.c_str(), std::ios_base::out | std::ios_base::app);
	log_file << GetTimeString() << "\t\t";
	log_file << text << std::endl;
}

void Logger::LogWithInt(const std::string& text, const int& num) {
	std::ofstream log_file(path_.c_str(), std::ios_base::out | std::ios_base::app);
	log_file << GetTimeString() << "\t\t";
	log_file << text << " (" << num << ")" << std::endl;
}

std::string Logger::GetTimeString() const {
	time_t timer;
	time(&timer);
	struct tm* timeinfo = localtime(&timer);

	std::stringstream stream;

	stream << timeinfo->tm_mday << "." << timeinfo->tm_mon+1 << "." << timeinfo->tm_year+1900 << " ";
	stream << timeinfo->tm_hour << ":" << timeinfo->tm_min << ":" << timeinfo->tm_sec;

	return stream.str();
}

