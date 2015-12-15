#!/usr/bin/ruby
#Result 2655

race_time = 2503
max_distance = nil

File.open('input.txt', 'r') do |f|
  f.each_line do |line|
    person = line.scan(/[A-Z]\w+/)[0]
    speed = line.scan(/\d+/)[0].to_f
    time = line.scan(/\d+/)[1].to_f
    rest_time = line.scan(/\d+/)[2].to_f

    distance = (race_time / (time + rest_time)).floor * (speed * time) + [(race_time % (time + rest_time)), time].min * speed

    if max_distance == nil or max_distance < distance
      max_distance = distance
    end

    puts person.to_s + ' : ' + distance.to_s
  end
end

puts 'Winner distance : ' + max_distance.to_s