#!/usr/bin/ruby
#Result 709

people = []
best_happiness = nil

def find_happiness(first_person, second_person)
  File.readlines('input.txt').select { |l| l =~ /^#{first_person}/ and l =~ /#{second_person}\./ }[0].gsub(/gain /, '+').gsub(/lose /, '-').scan(/-?\d+/)[0]
end

#Search all people
File.open('input.txt', 'r') do |f|
  f.each_line do |line|
    line.scan(/[A-Z]\w+/).each do |person|
      unless people.include? person
        people.push(person)
      end
    end
  end
end

people.permutation.reject { |array| array[0] != people[0] }.each { |path|
  happiness = 0

  (0...path.size).each { |i|
    previous_person = path[i-1]
    current_person = path[i]
    next_person = path[i == path.size - 1 ? 0 : i+1]

    happiness += find_happiness(current_person, next_person).to_i
    happiness += find_happiness(current_person, previous_person).to_i
  }

  if best_happiness == nil or best_happiness < happiness
    best_happiness = happiness
  end
}

puts 'Happiness : ' + best_happiness.to_s