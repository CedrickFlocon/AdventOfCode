#!/usr/bin/ruby
#Brut force algorithm of Travelling salesman problem
#Result 909

#Find the distance between two city
def find_distance(city_from, city_to)
  File.readlines('input.txt').select { |l| l =~ /^(?=.*\b#{city_from}\b)(?=.*\b#{city_to}\b).*$/ }[0].scan(/\d+/)
end

cities = []
first_path = ''
longest_distance = 0

#Search all city
File.open('input.txt', 'r') do |f|
  f.each_line do |line|
    line.scan(/[A-Z]\w+/).each do |city|
      unless cities.include? city
        cities.push(city)
      end
    end
  end
end

(0..(cities.size - 1)).each do |i|
  first_path = first_path + i.to_s
end

distance = 0
first_path.scan(/\w/).permutation { |path|
  (0...path.size - 1).each { |i|
    distance += find_distance(cities[path[i].to_i], cities[path[i + 1].to_i])[0].to_i
  }

  if longest_distance == 0 or distance > longest_distance
    longest_distance = distance
  end
  distance = 0
}

puts 'Longest Distance : ' + longest_distance.to_s