package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Không để trống")
    @Column(unique = true)
    @Pattern(regexp = "^[A-HJ-NP-Z0-9]{17}$" , message = "Sai định dạng")
    private String frameCode;
    @NotEmpty(message = "Không để trống")
    @Column(unique = true)
    @Pattern(regexp = "^[A-HJ-NP-Z0-9]{14}$" , message = "Sai định dạng")
    private String machineCode;
    @NotEmpty(message = "Không để trống")
    @Column(unique = true)
    private String name;
    @PastOrPresent(message = "Không được chọn ngày trong tương lai")
    @NotNull(message = "Không để trống")
    private LocalDate productionDate;
    @Min(value = 10000 , message = "Giá thấp nhấp là 10000")
    @NotNull(message = "Không để trống")
    private Double price;
    @Min(value = 1, message = "Số lượng thấp nhấp là 1")
    @NotNull(message = "Không để trống")
    private Integer quantity;
    private String image;
    @ManyToOne
    @JoinColumn(name ="type_id", nullable = false)
    @NotNull(message = "Không để trống")
    private Type type;
    @ManyToOne
    @JoinColumn(name ="producer_id", nullable = false)
    @NotNull(message = "Không để trống")
    private Producer producer;
}
