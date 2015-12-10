#!/usr/bin/ruby
#Result 2341

require './point'

File.open('input.txt', 'r') do |f|

  move = lambda { |p, c|
    if c == '^'
      p.y += 1
    elsif c == 'v'
      p.y -= 1
    elsif c == '>'
      p.x += 1
    elsif c == '<'
      p.x -= 1
    end
    p
  }

  map = []
  santa_point = Point.new(0, 0)
  robot_point = Point.new(0, 0)

  map.push(santa_point)

  number_move = 0

  f.each_char do |c|

    if number_move % 2 == 0
      position = santa_point = move.call(santa_point.clone, c)
    else
      position = robot_point = move.call(robot_point.clone, c)
    end

    unless map.include?(position)
      map.push(position)
    end

    number_move +=1
  end
  puts map.length
end