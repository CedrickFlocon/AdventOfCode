#!/usr/bin/ruby
#Result 2081

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
  map.push(santa_point)

  f.each_char do |c|
    santa_point = move.call(santa_point.clone, c)

    unless map.include?(santa_point)
      map.push(santa_point)
    end

  end
  puts map.length
end