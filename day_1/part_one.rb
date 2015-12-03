#!/usr/bin/ruby

File.open("input.txt", 'r') do |f|
  floor = 0
  f.each_char do |c|
    if c == '('
      floor +=1
    elsif c == ')'
      floor -=1
    end
  end
  puts floor
end