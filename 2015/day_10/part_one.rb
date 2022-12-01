#!/usr/bin/ruby
#Look-and-say algorithm
#Result 492982

input = '1321131112'
result = ''
saved_char = ''
saved_char_occurrence = 0

(0..39).each do |i|
  input.scan(/\w/).each { |c|
    if saved_char != '' and saved_char != c
      result << saved_char_occurrence.to_s + saved_char.to_s
      saved_char_occurrence = 1
    elsif saved_char_occurrence += 1
    end
    saved_char = c
  }

  result << saved_char_occurrence.to_s + saved_char.to_s

  saved_char = ''
  saved_char_occurrence = 0
  input = result
  result = ''
end

puts 'Result : ' + input.size.to_s