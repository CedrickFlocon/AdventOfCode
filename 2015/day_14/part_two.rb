#!/usr/bin/ruby
#Result 1059

require './person'

race_time = 2503
people = []

File.open('input.txt', 'r') do |f|
  f.each_line do |line|
    people << Person.new(line.scan(/[A-Z]\w+/)[0], line.scan(/\d+/)[0].to_f, line.scan(/\d+/)[1].to_f, line.scan(/\d+/)[2].to_f)
  end
end

(0..race_time).each { |second|
  first_person = nil
  people.each { |person|
    if second % person.cycle < person.run_time
      person.move(person.speed)
    end

    if first_person == nil or first_person.total_distance < person.total_distance
      first_person = person
    end
  }
  first_person.add_point
}

people.sort_by { |person| !person.points }
puts 'Winner point : ' + people[0].points.to_s