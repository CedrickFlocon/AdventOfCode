#!/usr/bin/ruby
#Result 668

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
  lowest_duo = nil

  (0...path.size).each { |i|
    previous_person = path[i-1]
    current_person = path[i]
    next_person = path[i == path.size - 1 ? 0 : i+1]

    happiness += find_happiness(current_person, next_person).to_i
    happiness += find_happiness(current_person, previous_person).to_i

    current_duo = find_happiness(current_person, next_person).to_i + find_happiness(next_person, current_person).to_i
    if lowest_duo == nil or lowest_duo > current_duo
        lowest_duo = current_duo
    end
  }
  happiness += -lowest_duo

  if best_happiness == nil or best_happiness < happiness
    best_happiness = happiness
  end
}

puts 'Happiness : ' + best_happiness.to_s